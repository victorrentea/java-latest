For example here is a method, handle(), that represents a task in a server application. It handles an incoming request by submitting two subtasks to an ExecutorService. One subtask executes the method findUser() and the other subtask executes the method fetchOrder(). The ExecutorService immediately returns a Future for each subtask, and executes each subtask in its own thread. The handle() method awaits the subtasks' results via blocking calls to their futures' get() methods, so the task is said to join its subtasks.

```java
Response handle() throws ExecutionException, InterruptedException {
    Future<String>  user  = esvc.submit(() -> findUser());
    Future<Integer> order = esvc.submit(() -> fetchOrder());
    String theUser  = user.get();   // Join findUser
    int    theOrder = order.get();  // Join fetchOrder
    return new Response(theUser, theOrder);
}
```

Because the subtasks execute concurrently, each subtask can succeed or fail independently. (Failure, in this context, means to throw an exception.) Often, a task such as handle() should fail if any of its subtasks fail. Understanding the lifetimes of the threads can be surprisingly complicated when failure occurs:

- If findUser() throws an exception then handle() will throw an exception when calling user.get() but fetchOrder() will continue to run in its own thread. This is a thread leak which, at best, wastes resources; at worst, the fetchOrder() thread will interfere with other tasks.

- If the thread executing handle() is interrupted, the interruption will not propagate to the subtasks. Both the findUser() and fetchOrder() threads will leak, continuing to run even after handle() has failed.

- If findUser() takes a long time to execute, but fetchOrder() fails in the meantime, then handle() will wait unnecessarily for findUser() by blocking on user.get() rather than cancelling it. Only after findUser() completes and user.get() returns will order.get() throw an exception, causing handle() to fail.