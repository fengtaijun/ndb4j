package org.pinae.ndb;

import java.util.Map;

import org.pinae.nala.xb.MarshalException;
import org.pinae.nala.xb.marshal.Marshaller;
import org.pinae.nala.xb.marshal.XMLMarshaller;

import com.alibaba.fastjson.JSON;

/**
 * ndb文件转换器
 * 
 * @author Huiyugeng
 * 
 * 
 */
public class Converter {
	/**
	 * 转换为JSON格式
	 * 
	 * @param config 解析后的配置信息
	 * @return JSON描述
	 */
	public String convertToJSON(Map<String, Object> config) {
		String jsonString = JSON.toJSONString(config);
		return jsonString;
	}

	/**
	 * 转换为XML格式
	 * 
	 * @param config 解析后的配置信息
	 * @return XML描述
	 */
	public String convertToXML(Map<String, Object> config) {

		String xmlString = "";
		try {
			Marshaller marshaller = new XMLMarshaller(config);
			marshaller.setDocumentStart("<?xml version='1.0' encoding='utf-8'?>");
			marshaller.enableNodeMode(true);

			xmlString = marshaller.marshal().toString();
		} catch (MarshalException e) {
			xmlString = "<exception>" + e.getMessage() + "</exception>";
		}

		return xmlString;
	}
}
