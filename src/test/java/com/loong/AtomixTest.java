package com.loong;

import io.atomix.cluster.Node;
import io.atomix.cluster.discovery.BootstrapDiscoveryProvider;
import io.atomix.core.Atomix;
import io.atomix.core.AtomixBuilder;
import io.atomix.core.profile.Profile;

public class AtomixTest {

    public static void main(String[] args) {

        AtomixBuilder builder = Atomix.builder();

        builder.addProfile(Profile.dataGrid());
        Atomix atomix = builder.withMemberId("member1")
                .withAddress("localhost:5001")
                .build();
        builder.withMembershipProvider(BootstrapDiscoveryProvider.builder()
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

       // Atomix atomix = builder.build();

        atomix.start().join();
        //builder.addProfiles(Profile.DATA_GRID);
    }
}
