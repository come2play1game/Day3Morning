package com.u2demo.testjdk.rx

import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.lang.Exception
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadFactory
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

fun main() {
//    testZipWith()
    testRx()
    Thread.sleep(100 * 1000)
}


fun testRx() {
    val executor = ThreadPoolExecutor(2, 10, 1, TimeUnit.MINUTES, LinkedBlockingQueue(), ThreadFactory {
        Thread(it).apply {
            name = "customThread"
        }
    })
    val subscribe = Observable.just(1)  //创建ObservableA
            .map {  // ObservableB(ObservableA)
                println("observer onSubscribe on thread:${Thread.currentThread()}")
                return@map it.toString()
            }
            .subscribeOn(Schedulers.from(executor))  // ObservableO(ObservableS)
            .subscribe {
                println("observer onNext on thread:${Thread.currentThread()}")

                println("ret:$it")
            }


    //  ObservableO#subscribe(Observer)
    //      ObservableO#subscribeActual(Observer)  //不切换线程
    //          ObservableS#subscribe(ObserverO(Observer))


    //                  <ObserverO#onSubscribe(DisposeS(ObserverO))
    //                  <Observer#onSubscribe(ObserverO) //原始observer监听到onSubscribe

    //              ObservableS#subscribeActual(ObserverO) //切换到subscribeOn指定的线程
    //                  ObservableB#subscribe(ObserverS(ObserverO))
    //                      ObservableB#subscribeActual(ObserverS)
    //                          ObservableA#subscribe(ObserverB(ObserverS))


    //                          ObservableA#subscribeActual(ObserverB)
    //                            执行原始observable的任务

    //                                  | |

    //                          ObserverB#onSubscribe(DisposeB(ObserverB))

    //                          ObserverB#onNext
    //                              ObserverS#onNext   //不切换线程
    //                                  ObserverO#onNext //切换到observeOn指定的线程
    //                                    Observer#onNext //原始observer执行onNext
    //


}

abstract class MyObservable {

    /**
     * final方法，强制子类走subscribeActual#抽象方法
     */
    fun subscribe(observer: MyObserver) {
        //使用try/catch包裹，做异常控制
        try {
            subscribeActual(observer)
        } catch (e: Exception) {

        }
    }

    /**
     * 每一行RX 操作符，都会对上一行的Observable进行包装产生新的 Observable
     * 到最后，Observable 会形成一个从下到上的单向链表
     *
     * Rx操作符，
     * 通过 重写subscribeActual，接收下层传来的事件然后穿给上层，期间可以控制事件的走向
     * && 在该方法中 使用了自定义的Observer包装类，对上层传来的事件进行控制，然后传给下层
     * 类似拦截链？
     */
    fun operate(): MyObservable {
        return MyObservableWrapper(this)
    }

    /**
     *给子类提供的抽象方法，
     */
    abstract fun subscribeActual(observer: MyObserver)
}

class MyObservableWrapper(val source: MyObservable) : MyObservable() {

    /**
     * 该方法的核心
     * 1.  向上传递 执行指令，递归触发第一个Observable#subscribeActual。
     * （原理：调用上游的 subscribe ，间接调用 上游的subscribeActual。这样在链式调用时，会从最后一行递归到第一行(也就是我们自己主动显式创建的） Observable，执行我们的业务逻辑。）
     *
     * 有部分操作符(eg. subscribeOn) 创建出来的Observable，它的subscribeActual方法会切换线程
     * 所以subscribeOn的作用就是 在向上（并且只在向上）传递事件时切换到指定的线程
     * 当有多个subscribeOn时，就会切换多次，我们业务逻辑最终所在的线程为离它最近的subscribeOn，如果没指定，则为当前线程
     *
     * 2. 通过添加自定义的observer包装类，控制住向下的事件流
     * 每一层添加对应包装类 来hold下一层的Observer，形成 一个从上到下的单向链表
     * 到最上层执行完毕，通知observer时，会一层一层剥开包装类，让事件一层一层往下传递
     *
     * 有部分操作符(eg. observerOn) 创建出来的Observer包装类，它的onSubscribe/onNext/onError/onCompleted 都会切换到指定线程
     * 所以observerOn 的作用就是 在向下（并且只在向下）传递事件时切换到指定的线程
     * 当有多个observerOn时，就会切换多次，我们的回调业务逻辑最终所在的执行线程为 离它最近的observerOn，如果没指定，则为subscribeOn所指定的线程(如果没指定，则为最初的执行线程)
     *
     *
     */
    override fun subscribeActual(observer: MyObserver) {
        //doSomething
        source.subscribe(MyCustomObserver(observer))
        //doSomething
    }

}

interface MyObserver {

    fun onSubscribe(d: MyDisposable)
}

class MyCustomObserver(val actual: MyObserver) : MyObserver {

    /**
     * 1. Observer是一个从上到下的单向链表，触发第一个节点onSubscribe会
     * 从上向下传递onSubscribe事件  递归触发最下游的onSubscribe方法，
     *
     * 2.传递过程，每层都可以对上层的disposable进行包装。这些disposable 形成了 从下到上的单向链表
     * 当第一个结点执行dispose方法时，会引发 从下到上递归dispose
     */
    override fun onSubscribe(d: MyDisposable) {
        actual.onSubscribe(MyCustomDisposableWrapper(d))
    }
}


interface MyDisposable {
    fun dispose()
    fun isDispose(): Boolean
}

open class MyCustomDisposable : MyDisposable {
    override fun dispose() {
        TODO("Not yet implemented")
    }

    override fun isDispose(): Boolean {
        TODO("Not yet implemented")
    }
}

open class MyCustomDisposableWrapper(val upstream: MyDisposable) : MyDisposable {

    /**
     * disposable是一个从下到上的单向链表
     * 当第一个结点执行dispose方法时，会引发 从下到上递归dispose
     */
    override fun dispose() {
        // 对自己的dispose && 对上游做dispose
        upstream.dispose()
    }

    override fun isDispose(): Boolean {
        TODO("Not yet implemented")
    }
}


fun testZipWith() {


    val like = Observable.create<Boolean> {

        it.onNext(true)
        println("send 1 ${Thread.currentThread()}")
        Thread.sleep(400)
        it.onNext(false)
        println("send 2 ${Thread.currentThread()}")
        Thread.sleep(400)
        it.onNext(true)
        println("send 3 ${Thread.currentThread()}")
        Thread.sleep(400)
        println("send 4 ${Thread.currentThread()}")
        Thread.sleep(500)

        it.onNext(false)
    }


    val debounce = like.debounce(1000, TimeUnit.MILLISECONDS)
//    val disposed = debounce.zipWith(
//            debounce.startWith(false).doAfterNext {
//                println("debounce.startWith doAfterNext${it}, ${Thread.currentThread()}")
//            },
//            BiFunction<Boolean, Boolean, Pair<Boolean, Boolean>> { last, current ->
//                println("zipWith $last,$current.${Thread.currentThread()}")
//                val ret = if (last == current) Pair(false, false) else Pair(true, last)
//                return@BiFunction ret
//            }
//    ).subscribe {
//        if (it.first) {
//
//        } else {
//
//        }
//    }


//    debounce.subscribe {
//        println("ret:$it,${Thread.currentThread()}")
//    }

    val o1 = Observable.just(1, 2, 3)
    val o2 = Observable.just(4, 5, 6)
//    o1.zipWith(
//            o1.startWith(0),
//            BiFunction<Int, Int, Int> { p1, p2 ->
//                println("p1:$p1,p2:$p2,return ${p1 + p2}")
//                return@BiFunction p1 + p2
//            }
//    ).buffer{
//
//    }
//            .subscribe {
//        println("onSubscribe:$it")
//
//    }


    Thread.sleep(100000)
}