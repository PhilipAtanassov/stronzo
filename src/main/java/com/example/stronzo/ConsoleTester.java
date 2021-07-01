package com.example.stronzo;

import com.example.stronzo.clients.CatFactClientImpl;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConsoleTester {

    public static void main(String[] args) {
        var cfClient = new CatFactClientImpl();
        var mono = cfClient.getCatFactsMono()
                // parallel I think 🤑
                .zipWith(cfClient.getCatFactsMono())
                .map(tuple -> Stream
                        .concat(tuple.getT1().stream(), tuple.getT2().stream())
                        .collect(Collectors.toList()));

        mono.block().forEach(cf -> System.out.println(cf.text));
    }
}
