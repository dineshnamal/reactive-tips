package com.example.fluxmonodemo;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.function.Function;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class FluxMonoServiceImpl implements FluxMonoService {

	@Override
	public Flux<String> nameFlux() {
		// Creating a Flux with fromIterable
		return Flux.fromIterable(List.of("Bob", "Alice", "Chuck"));
	}

	@Override
	public Mono<String> nameMono() {
		// TODO Auto-generated method stub
		return Mono.just("Alex");
	}

	@Override
	public Flux<String> nameFluxLog() {
		// Creating a Flux with fromIterable
		return Flux.fromIterable(List.of("Bob", "Alice", "Chuck")).log();
	}

	@Override
	public Mono<String> nameMonoLog() {
		// TODO Auto-generated method stub
		return Mono.just("Alex").log();
	}

	@Override
	public Flux<String> nameFluxTransFormLog() {
		return Flux.fromIterable(List.of("Bob", "Alice", "Chuck")).map(String::toUpperCase).log();
	}

	@Override
	public Flux<String> nameFluxTransForm() {
		Flux<String> fluxString = Flux.fromIterable(List.of("Bob", "Alice", "Chuck"));
		fluxString.map(String::toUpperCase);
		return fluxString;
	}

	@Override
	public Flux<String> nameFluxMapFilter(int length) {
		return Flux.fromIterable(List.of("Bob", "Alice", "Chuck")).map(String::toUpperCase)
				.filter(x -> x.length() > length).map(s -> s.length() + "-" + s).log();
	}

	@Override
	public Flux<String> nameFluxFlatMapFilter(int length) {
		return Flux.fromIterable(List.of("Bob", "Alice", "Chuck")).map(String::toUpperCase)
				.filter(x -> x.length() > length).flatMap(s -> getSplitString(s));
	}

	@Override
	public Flux<String> nameFluxFlatMapFilterAsync(int length) {
		return Flux.fromIterable(List.of("Bob", "Alice", "Chuck")).map(String::toUpperCase)
				.filter(x -> x.length() > length).flatMap(s -> getSplitStringWithDelayAsync(s));
	}

	@Override
	public Flux<String> nameFluxConcatMapFilterAsync(int length) {
		return Flux.fromIterable(List.of("Bob", "Alice", "Chuck")).map(String::toUpperCase)
				.filter(x -> x.length() > length).concatMap(s -> getSplitStringWithDelayAsync(s));
	}

	public Mono<String> nameMonoMapFilter(int strLength) {
		return Mono.just("bob").map(String::toUpperCase).filter(s -> s.length() > strLength);
	}

	public Mono<List<String>> nameMonoFlatMapFilter(int strLength) {
		return Mono.just("bobs").map(String::toUpperCase).filter(s -> s.length() > strLength)
				.flatMap(this::splitStringMono);
	}

	public Mono<List<String>> namesMono_flatMap(int stirngLength) {
		return Mono.just("alex").map(String::toUpperCase).filter(s -> s.length() > stirngLength)
				.flatMap(this::splitStringMono2).log(); // Mono<List of A, L, E X>

	}

	public Flux<List<String>> namesMono_flatMapMany(int stirngLength) {
		return Mono.just("alex").map(String::toUpperCase).filter(s -> s.length() > stirngLength)
				.flatMapMany(this::splitStringMono2).log(); // Mono<List of A, L, E X>

	}

	
//	public Flux<String> nameFlux_transform(int stringLength) {
//		Function<Flux<String>, Flux<String>> filterMap = name -> name.map(String::toUpperCase)
//				.filter(s -> s.length() > stringLength);
//		return Flux.fromIterable(List.of("alex", "chuck", "bob")).transform(filterMap).defaultIfEmpty("default").log();
//	}
	@Override
    public Flux<String> namesFlux_transform(int stringLength) {
        //filter the string whose length is greater than 3

        Function<Flux<String>,Flux<String>> filterMap = name -> name.map(String::toUpperCase)
                .filter(s->s.length()> stringLength);

        //Flux.empty()
        
        return Flux.fromIterable(List.of("alex", "ben","chuck"))
                .transform(filterMap)
                .flatMap(s-> splitString(s)) // A,L,E,X,C,H,L,O,E
                .defaultIfEmpty("default")
                .log(); // db or a remote service call
         
    }

	
	  public Flux<String> splitString(String name){
	        var charArray = name.split("");
	        return Flux.fromArray(charArray);
	    }
	@Override
	public Flux<String> nameFlux_transform_switch_if_empty(int stringLength) {
		Function<Flux<String>, Flux<String>> filterMap = name -> name.map(String::toUpperCase)
				.filter(s -> s.length() > stringLength);
		
		var defaultFlux = Flux.just("default").transform(filterMap);
				
		return Flux.fromIterable(List.of("alex", "chuck", "bob")).transform(filterMap).switchIfEmpty(defaultFlux).flatMap(s -> getSplitString(s)).log();
	}
	
	 public Flux<String> namesFlux_transform_switchifEmpty(int stringLength) {
	        //filter the string whose length is greater than 3

	        Function<Flux<String>,Flux<String>> filterMap = name ->
	                name.map(String::toUpperCase)
	                .filter(s->s.length()> stringLength)
	                .flatMap(s-> splitString(s));

	        var defaultFlux = Flux.just("default")
	                .transform(filterMap); //"D","E","F","A","U","L","T"

	        //Flux.empty()
	        return Flux.fromIterable(List.of("alex", "ben","chloe"))
	                .transform(filterMap)
	                 // A,L,E,X,C,H,L,O,E
	                .switchIfEmpty(defaultFlux)
	                .log(); // db or a remote service call
	    }


	private Mono<List<String>> splitStringMono2(String s) {
		var charArray = s.split("");
		var charList = List.of(charArray); // ALEX -> A, L, E, X
		return Mono.just(charList);
	}

	private Flux<String> getSplitStringWithDelayAsync(String name) {
		var charArray = name.split("");
		var delay = new Random().nextInt(1000);

		return Flux.fromArray(charArray).delayElements(Duration.ofMillis(delay));
	}

	private Flux<String> getSplitString(String name) {
		var charArray = name.split("");
		return Flux.fromArray(charArray);
	}

	private Mono<List<String>> splitStringMono(String name) {
		var charArray = name.split("");
		return Mono.just(List.of(charArray));
	}

	public static void main(String[] args) {

		FluxMonoService fluxMonoService = new FluxMonoServiceImpl();
		Flux<String> nameFluxes = fluxMonoService.nameFlux();
		nameFluxes.subscribe(name -> {
			System.out.println(name);
		});

		Mono<String> nameMono = fluxMonoService.nameMono();

		nameMono.subscribe(name -> {
			System.out.println(name);
		});

		Flux<String> nameFilterFluxes = fluxMonoService.nameFluxMapFilter(3);
		nameFilterFluxes.subscribe(name -> {
			System.out.println(name);
		});
	}

	@Override
	public Flux<String> nameFlux_transform(int stringLength) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Flux<String> exploreConcat() {
		var abcFlux = Flux.just("A","B","C");
		var defFlux = Flux.just("D","E","F");
		return Flux.concat(abcFlux,defFlux);
	
	}
	
	@Override
	public Flux<String> exploreConcatWith() {
		var abcFlux = Flux.just("A","B","C");
		var defFlux = Flux.just("D","E","F");
		return abcFlux.concatWith(defFlux);	
	}
	
	@Override
	public Flux<String> exploreConcatWithMono() {
		var aMono = Mono.just("A");
		var bMono = Mono.just("B");		
		return aMono.concatWith(bMono).log();
		
	}

	@Override
	public Flux<String> exploreMerge() {

        var abcFlux = Flux.just("A","B","C")
                .delayElements(Duration.ofMillis(100)); //A,B

        var defFlux = Flux.just("D","E","F")
                .delayElements(Duration.ofMillis(125));//D,E

        return Flux.merge(abcFlux,defFlux).log();
	}

	@Override
    public Flux<String> exploreMergeWith(){

        var abcFlux = Flux.just("A","B","C")
                .delayElements(Duration.ofMillis(100)); //A,B

        var defFlux = Flux.just("D","E","F")
                .delayElements(Duration.ofMillis(125));//D,E

        return abcFlux.mergeWith(defFlux).log();

    }
	
	
	@Override
	public Flux<String> exploreMergeWithMono() {
		var aMono = Mono.just("A"); //A

        var bMono = Mono.just("B"); //B

        return aMono.mergeWith(bMono).log(); // A, B
	}


	@Override
    public Flux<String> exploreMergeWithSequential(){

        var abcFlux = Flux.just("A","B","C")
                .delayElements(Duration.ofMillis(100)); //A,B

        var defFlux = Flux.just("D","E","F")
                .delayElements(Duration.ofMillis(125));//D,E

        return Flux.mergeSequential(abcFlux,defFlux).log();

    }
}
