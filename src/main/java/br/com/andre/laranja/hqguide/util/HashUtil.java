package br.com.andre.laranja.hqguide.util;

import java.nio.charset.StandardCharsets;

import com.google.common.hash.Hashing;

public class HashUtil {
	public static String hash256(String palavra) {
		//"tempero" para o hash
		String salt = "l@r@nj@";
		palavra = palavra + salt;
		//criar o hash e armazenar na String
		String sha256 = Hashing.sha256().hashString(palavra, StandardCharsets.UTF_8).toString();
		//retorna o hash
		return sha256;
	}
}
