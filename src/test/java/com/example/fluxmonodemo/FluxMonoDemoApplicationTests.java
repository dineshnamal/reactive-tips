package com.example.fluxmonodemo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class FluxMonoDemoApplicationTests {
	FluxMonoService fluxMonoService = new FluxMonoServiceImpl();
	
	@Test
	void contextLoads() {
	}
	
	@Test
	void testNameFlux() {
		//Accessing Flux
		Flux<String> nameFluxes = fluxMonoService.nameFlux();
//		nameFluxes.subscribe(name-> {
//			assertTrue(List.of("Bob","Alice","Chuck").contains(name));
//		});
		StepVerifier.create(nameFluxes).expectNext("Bob","Alice","Chuck").verifyComplete();
		//StepVerifier.create(nameFluxes).expectNextCount(3).verifyComplete();
		StepVerifier.create(nameFluxes).expectNext("Bob").expectNextCount(2).verifyComplete();
	}

	@Test
	void testAccessMono() {
		//Accessing Flux
		Mono<String> nameFluxes = fluxMonoService.nameMono();
//		nameFluxes.subscribe(name-> {
//			assertEquals("Alex", name);;
//		});
		StepVerifier.create(nameFluxes).expectNext("Alex").verifyComplete();
	}
	
	
	@Test
	void testAccessFluxLog() {
		//Accessing Flux
		Flux<String> nameFluxes = fluxMonoService.nameFluxLog();
		nameFluxes.subscribe(name-> {
			assertTrue(List.of("Bob","Alice","Chuck").contains(name));
		});
	}
	
	@Test
	void testAccessMonoLog() {
		//Accessing Flux
		Mono<String> nameFluxes = fluxMonoService.nameMonoLog();
		nameFluxes.subscribe(name-> {
			assertEquals("Alex", name);;
		});
	}
	
	@Test
	void nameFluxTransFormLog() {
		var nameFluxTransform = fluxMonoService.nameFluxTransFormLog();
		StepVerifier.create(nameFluxTransform).expectNext("BOB","ALICE","CHUCK").verifyComplete();
	}
	
	@Test
	void nameFluxTransForm() {
		var nameFluxTransform = fluxMonoService.nameFluxTransForm();
		StepVerifier.create(nameFluxTransform).expectNext("Bob","Alice","Chuck").verifyComplete();
	}
	
	@Test
	void nameFluxFilter() {
		var  nameFluxFilter = fluxMonoService.nameFluxMapFilter(3);
		StepVerifier.create(nameFluxFilter).expectNext("5-ALICE","5-CHUCK").verifyComplete();
	}
	
	@Test
	void nameFluxFlatMapFilter() {
		var  nameFluxFlatMapFilter = fluxMonoService.nameFluxFlatMapFilter(3);
		StepVerifier.create(nameFluxFlatMapFilter).expectNext("A","L","I","C","E","C","H","U","C","K").verifyComplete();
	}
	
	@Test
	void nameFluxFlatMapFilterAsync() {
		var  nameFluxFlatMapFilterAsync = fluxMonoService.nameFluxFlatMapFilterAsync(3);
		StepVerifier.create(nameFluxFlatMapFilterAsync)
		//.expectNext("A","L","I","C","E","C","H","U","C","K").verifyComplete();
		.expectNextCount(10).verifyComplete();
	}
	
	@Test
	void nameFluxConcatMapFilterAsync() {
		var  nameFluxConcatMapFilterAsync = fluxMonoService.nameFluxConcatMapFilterAsync(3);
		StepVerifier.create(nameFluxConcatMapFilterAsync)
		.expectNext("A","L","I","C","E","C","H","U","C","K").verifyComplete();
	}
	
	@Test
	 void nameMonoFlatMapFilter(){
		int strLength=3;
		var nameMonoFlatMap= fluxMonoService.nameMonoFlatMapFilter(strLength);
			StepVerifier.create(nameMonoFlatMap)
			.expectNext(List.of("B","O","B","S"))
			.verifyComplete();

	 }
	
	 @Test
	    void namesMono_flatMap() {
	        //given
	        int stringLength = 3;

	        //when
	        var value = fluxMonoService.namesMono_flatMap(stringLength);

	        //then
	        StepVerifier.create(value)
	                .expectNext(List.of("A","L","E","X"))
	                .verifyComplete();
	    }
	 
	 
		
	 @Test
	    void namesMono_flatMapMany() {
	        //given
	        int stringLength = 3;

	        //when
	        var value = fluxMonoService.namesMono_flatMapMany(stringLength);

	        //then
	        StepVerifier.create(value)
	                .expectNext(List.of("A","L","E","X"))
	                .verifyComplete();
	    }
	 
	 @Test
	 void nameFlux_transform() {
		 int stringLength = 3;
	        var nameFlux_transformValue = fluxMonoService.namesFlux_transform(stringLength);

		 StepVerifier.create(nameFlux_transformValue).expectNext("A","L","E","X","C","H","U","C","K").verifyComplete();
	 }

	 @Test
	 void nameFlux_transform_higher_length() {
		 int stringLength = 6;
	     var nameFlux_transformValue = fluxMonoService.namesFlux_transform(stringLength);
		 StepVerifier.create(nameFlux_transformValue).expectNext("default").verifyComplete();
	 }
	 
	    @Test
	    void namesFlux_transform_switchifEmpty() {
	        //given
	        int stringLength = 6;

	        //when
	        var namesFlux = fluxMonoService.namesFlux_transform_switchifEmpty(stringLength);

	        //then
	        StepVerifier.create(namesFlux)
	                //.expectNext("A","L","E","X","C","H","L","O","E")
	                .expectNext("D","E","F","A","U","L","T")
	                .verifyComplete();
	    }
	    
	    
	    
	    
	    @Test 
	    void exploreConcat() {
	    	Flux<String> concatnatedString = fluxMonoService.exploreConcat();
	    	StepVerifier.create(concatnatedString).expectNext("A","B","C","D","E","F").verifyComplete();
	    }
	    
	    

	    
	    @Test 
	    void exploreConcatWith() {
	    	Flux<String> concatnatedString = fluxMonoService.exploreConcatWith();
	    	StepVerifier.create(concatnatedString).expectNext("A","B","C","D","E","F").verifyComplete();
	    }
	    

	    
	    @Test 
	    void exploreConcatWithMono() {
	    	Flux<String> concatnatedString = fluxMonoService.exploreConcatWithMono();
	    	StepVerifier.create(concatnatedString).expectNext("A","B").verifyComplete();
	    }
	    
	    
	    
	    
	    
	 
//	 @Test
//	 void nameFlux_transform_switch_if_empty() {
//		 int stringLength = 6;
//	        var nameFlux_transformValue = fluxMonoService.nameFlux_transform_switch_if_empty(stringLength);
//
//		 StepVerifier.create(nameFlux_transformValue).expectNext("default").verifyComplete();
//
//	 
//	 }
	 
	 
}
