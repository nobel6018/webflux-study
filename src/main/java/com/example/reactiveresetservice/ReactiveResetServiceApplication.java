package com.example.reactiveresetservice;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class ReactiveResetServiceApplication {

    public static void main(String[] args) {
        //        ConfigurableApplicationContext context = SpringApplication.run(ReactiveResetServiceApplication.class, args);
        //
        //        GreetingClient greetingClient = context.getBean(GreetingClient.class);
        //        // We need to block for the content here or the JVM might exit before the message is logged
        //        System.out.println(">> message = " + greetingClient.getMessage().block());

        String text = "STRING";
        Mono<String> mono = Mono.just(text);
        mono.subscribe(s -> {
            System.out.println(s);
        });

        one();
    }

    private static Publisher<String> publisher = new Publisher<String>() {
        @Override
        public void subscribe(Subscriber<? super String> s) {
            Subscription subscription = new Subscription() {
                @Override
                public void request(long n) {
                    s.onNext("lorem ipsum");
                    s.onNext("EFG");
                    s.onComplete();
                }

                @Override
                public void cancel() {

                }
            };
            s.onSubscribe(subscription);
        }
    };

    private static void one() {
        Mono.from(publisher).map(s -> s.length()).subscribe(integer -> {
            System.out.println("Mono: " + integer);
        });

        Flux.from(publisher).map(s -> s.length()).subscribe(integer -> {
            System.out.println("Flux: " + integer);
        });
    }

}
