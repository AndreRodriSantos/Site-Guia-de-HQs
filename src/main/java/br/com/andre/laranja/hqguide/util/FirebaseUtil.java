package br.com.andre.laranja.hqguide.util;

import java.io.IOException;
import java.util.UUID;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

@Service
public class FirebaseUtil {
	// variavel para guardar a credenciais da fireBase
	private Credentials credenciais;
	// variavel para acessar o storage
	private Storage storage;
	// constante para o nome do bucket
	private final String BUCKET_NAME = "hqsguide.appspot.com";
	// constante para o prefixo da URL
	private final String PREFIX = "https://firebasestorage.googleapis.com/v0/b/" + BUCKET_NAME + "/o/";
	// constante para o sufixo da URL
	private final String SUFFIX = "?alt=media";
	// constante para a URL
	private final String DOWNLOAD_URL = PREFIX + "%s" + SUFFIX;
	
	public FirebaseUtil() {
		// buscar as credenciais (arquivo JSON)
		Resource resource = new ClassPathResource("chaveFireBase.json");
		try {
			// ler o arquivo para obter as credenciais
			credenciais = GoogleCredentials.fromStream(resource.getInputStream());
			// acessa o storage
			storage = StorageOptions.newBuilder().setCredentials(credenciais).build().getService();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	
	private String getExtensao(String nomeArq) {
		//retorna o trecho da String que vai do ultimo ponto ate o fim
		return nomeArq.substring(nomeArq.lastIndexOf('.'));
	}
	
	public String uploadFile(MultipartFile arquivo) throws IOException {
		//gera uma String aleatoria para o nome do arquivo
		String nomeArquivo = UUID.randomUUID().toString() + getExtensao(arquivo.getOriginalFilename());
		
		//criar um Blob
		BlobId blobId = BlobId.of(BUCKET_NAME, nomeArquivo);
		//criar um BlobInfo a paratir do blob id
		BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
		//manda o blob info para o storage passando os bytes do arquivo
		storage.create(blobInfo, arquivo.getBytes());
		//retornar a url para acessar o arquivo
		return String.format(DOWNLOAD_URL, nomeArquivo);
	}
	
	//metodo para excluir a foto do FireBase
	public void deletar(String nomeFoto) {
		//retira o prefixo e o sufixo do nome do arquivo
		nomeFoto = nomeFoto.replace(PREFIX, "").replace(SUFFIX, "");
		//pega um blob atraves do nome do arquivo
		Blob blob = storage.get(BlobId.of(BUCKET_NAME, nomeFoto));
		//deleta a foto
		storage.delete(blob.getBlobId());
	}
}
