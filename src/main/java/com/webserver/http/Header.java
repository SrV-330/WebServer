package com.webserver.http;


import com.webserver.exception.IllegalRequestException;

public class Header {
	
	private String header;
	private String value;
	
	

	
	public Header(String line) throws Exception{
		parser(line);
	}
	public Header(String header,String value) throws Exception{
		parser(header,value);
	}
	
	
	
	
	public String getHeader() {
		return header;
	}

	public String getValue() {
		return value;
	}

	public void parser(String header,String value) throws Exception{
		
		if(header==null||value==null) throw new IllegalRequestException("header or value must be not null");
		if(header.length()==0||value.length()==0) throw new IllegalRequestException("header or value must be not empty");
		this.header=header;
		this.value=value;
		
	}
	public void parser(String line) throws Exception{
		
		if(line==null) throw new IllegalRequestException("header or value must be not null");
		String lines[] =line.split(":\\s{1,}");
		if(lines.length!=2) throw new IllegalRequestException("header or value must be not null");
		lines[0]=lines[0].trim();
		lines[1]=lines[1].trim();
		parser(lines[0],lines[1]);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((header == null) ? 0 : header.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Header other = (Header) obj;
		if (header == null) {
			if (other.header != null)
				return false;
		} else if (!header.equals(other.header))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "( " + header + ", " + value + " ) ";
	}
	
	
	
	

}
