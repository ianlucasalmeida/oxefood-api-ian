package br.com.ifpe.oxefood.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.springframework.web.multipart.MultipartFile;

public class Util {

    // Pasta onde as imagens serão salvas no seu computador (Fedora)
    // Ajustei o caminho para ficar dentro da pasta do seu projeto para facilitar
    public static final String LOCAL_ARMAZENAMENTO_IMAGENS = "/home/ianlucasalmeida/Github/oxefood-api-ian/imagens_cadastradas/";

    public static boolean fazerUploadImagem(MultipartFile imagem) {

        boolean sucessoUpload = false;

        if (!imagem.isEmpty()) {
            String nomeArquivo = imagem.getOriginalFilename();
            try {
                // Cria a pasta automaticamente caso ela não exista
                File dir = new File(LOCAL_ARMAZENAMENTO_IMAGENS);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                // Cria o arquivo físico no disco
                File serverFile = new File(dir.getAbsolutePath() + File.separator + nomeArquivo);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                
                stream.write(imagem.getBytes());
                stream.close();
                
                System.out.println("Arquivo armazenado em: " + serverFile.getAbsolutePath());
                sucessoUpload = true;

            } catch (Exception e) {
                System.out.println("Erro ao fazer upload da imagem: " + e.getMessage());
            }
        }
        return sucessoUpload;
    }
}