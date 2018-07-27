package com.loong;

import io.atomix.cluster.Node;
import io.atomix.cluster.discovery.BootstrapDiscoveryProvider;
import io.atomix.core.Atomix;
import io.atomix.core.AtomixBuilder;
import io.atomix.core.map.DistributedMap;

public class JavaClientTest {
    public static void main(String[] args) {
        AtomixBuilder atomic = Atomix.builder()
                .withMemberId("client1")
                .withAddress("localhost:6000")
                .withMembershipProvider(BootstrapDiscoveryProvider.builder()
                        .withNodes(
                                Node.builder()
                                        .withId("member1")
                                        .withAddress("localhost:5001")
                                        .build(),
                                Node.builder()
                                        .withId("member2")
                                        .withAddress("localhost:5002")
                                        .build(),
                                Node.builder()
                                        .withId("member3")
                                        .withAddress("localhost:5003")
                                        .build())
                        .build());
        Atomix atomix = atomic.build();

        atomix.start().join();

        DistributedMap<Object, Object> map = atomix.mapBuilder("my-map")
                .withCacheEnabled()
                .build();

        map.put("foo", "Hello world!");

        Object value = map.get("foo");

        if (map.replace("foo", value, "Hello world again!")) {
            System.out.println("测试");
        }
    }
}
