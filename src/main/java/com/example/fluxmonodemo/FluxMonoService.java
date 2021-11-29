package com.example.fluxmonodemo;

import java.util.List;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FluxMonoService {

	public Flux<String> nameFlux();

	public Mono<String> nameMono();

	// Log each and every event happens between subscriber and publisher
	public Flux<String> nameFluxLog();

	// Log each and every event for Mono
	public Mono<String> nameMonoLog();

	public Flux<String> nameFluxTransFormLog();

	public Flux<String> nameFluxTransForm();

	public Flux<String> nameFluxMapFilter(int length);

	public Flux<String> nameFluxFlatMapFilter(int length);

	public Flux<String> nameFluxFlatMapFilterAsync(int length);

	public Flux<String> nameFluxConcatMapFilterAsync(int length);

	public Mono<String> nameMonoMapFilter(int strLength);

	public Mono<List<String>> nameMonoFlatMapFilter(int strLength);

	public Mono<List<String>> namesMono_flatMap(int stirngLength);

	public Flux<List<String>> namesMono_flatMapMany(int stirngLength);

	public Flux<String> nameFlux_transform(int stringLength);

	public Flux<String> namesFlux_transform(int stringLength);

	public Flux<String> nameFlux_transform_switch_if_empty(int stringLength);

	public Flux<String> namesFlux_transform_switchifEmpty(int stringLength);

	public Flux<String> exploreConcat();

	public Flux<String> exploreConcatWith();

	public Flux<String> exploreConcatWithMono();

	public Flux<String> exploreMerge();

	public Flux<String> exploreMergeWith();

	public Flux<String> exploreMergeWithMono();

	public Flux<String> exploreMergeWithSequential();
}