/*
 * Copyright 2005-2013 shopxx.net. All rights reserved.
 * Support: http://www.shopxx.net
 * License: http://www.shopxx.net/license
 */
package com.hyeb;

import java.math.BigDecimal;

import org.apache.lucene.document.Document;
import org.hibernate.search.bridge.FieldBridge;
import org.hibernate.search.bridge.LuceneOptions;
import org.hibernate.search.bridge.TwoWayFieldBridge;

/**
 * BigDecimal类型转换
 * 
 * @author SHOP++ Team
 * @version 3.0
 */
public class BigDecimalNumericFieldBridge implements  FieldBridge, TwoWayFieldBridge  {
    private static final BigDecimal storeFactor = BigDecimal.valueOf(100);

    @Override
    public void set(String name, Object value, Document document, LuceneOptions luceneOptions) {
        if ( value != null ) {
            BigDecimal decimalValue = (BigDecimal) value;
            long tmpLong = decimalValue.multiply( storeFactor ).longValue();
            Long indexedValue = Long.valueOf( tmpLong );
            luceneOptions.addNumericFieldToDocument( name, indexedValue, document );
        }
    }

    @Override
    public Object get(String name, Document document) {
        String fromLucene = document.get( name );
        BigDecimal storedBigDecimal = new BigDecimal( fromLucene );
        return storedBigDecimal.divide( storeFactor );
    }
    
    @Override
	public final String objectToString(final Object object) {
		return object == null ? null : object.toString();
	}
}