//
// Copyright (c) 2011-2014 AldoGrand Consultancy Ltd., 
//

package com.aldogrand.kfc.msg.producer.kafka;

import kafka.serializer.Decoder;
import kafka.utils.VerifiableProperties;

import java.util.Properties;

public class StringDecoder implements Decoder<String> {
    private String encoding = "UTF8";

    public void setEncoding(final String encoding){
        this.encoding = encoding;
    }

    @Override
    public String fromBytes(byte[] bytes) {
        final Properties props = new Properties();
        props.put("serializer.encoding", encoding);

        final VerifiableProperties verifiableProperties = new VerifiableProperties(props);

        return new kafka.serializer.StringDecoder(verifiableProperties).fromBytes(bytes);
    }
}