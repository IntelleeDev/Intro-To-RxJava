package itrx.chapter2.transforming;

import java.util.Arrays;

import org.junit.Test;

import rx.Observable;
import rx.Subscriber;
import rx.observers.TestSubscriber;

public class MapExample {
	
	private static class PrintSubscriber extends Subscriber<Object>{
	    private final String name;
	    public PrintSubscriber(String name) {
	        this.name = name;
	    }
	    @Override
	    public void onCompleted() {
	        System.out.println(name + ": Completed");
	    }
	    @Override
	    public void onError(Throwable e) {
	        System.out.println(name + ": Error: " + e);
	    }
	    @Override
	    public void onNext(Object v) {
	        System.out.println(name + ": " + v);
	    }
	}

	public void exampleMap() {
		Observable<Integer> values = Observable.range(0,4);

		values
		    .map(i -> i + 3)
		    .subscribe(new PrintSubscriber("Map"));
		
		// Map: 3
		// Map: 4
		// Map: 5
		// Map: 6
		// Map: Completed
	}
	
	public void exampleMap2() {
		Observable<Integer> values = 
		        Observable.just("0", "1", "2", "3")
		            .map(Integer::parseInt);

		values.subscribe(new PrintSubscriber("Map"));
		
		// Map: 0
		// Map: 1
		// Map: 2
		// Map: 3
		// Map: Completed
	}
	
	
	//
	// Tests
	//
	
	@Test
	public void testMap() {
		TestSubscriber<Integer> tester = new TestSubscriber<>();
		
		Observable<Integer> values = Observable.range(0,4);

		values
		    .map(i -> i + 3)
		    .subscribe(tester);
		
		tester.assertReceivedOnNext(Arrays.asList(3,4,5,6));
		tester.assertTerminalEvent();
		tester.assertNoErrors();
	}
	
	@Test
	public void testMap2() {
		TestSubscriber<Integer> tester = new TestSubscriber<>();
		
		Observable<Integer> values = 
		        Observable.just("0", "1", "2", "3")
		            .map(Integer::parseInt);

		values.subscribe(tester);
		
		tester.assertReceivedOnNext(Arrays.asList(0,1,2,3));
		tester.assertTerminalEvent();
		tester.assertNoErrors();
	}

}
