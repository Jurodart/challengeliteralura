package br.com.alura.literalurachallenge.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.util.List;

//5) Convertendo os dados
//Basicamente a função do Gson
public class ConverteDados implements IConverteDados {
    //mapper ajuda a desserializar e mapear as classes do json
    private ObjectMapper mapper = new ObjectMapper();

    //Método genérico onde passamos o json e devolve
    //a classe que indicamos no método
    @Override
    public <T> T obterDados(String json, Class<T> classe) {
        //  antes de usar o try/catch:   return mapper.readValue(json, classe);
        try {
            return mapper.readValue(json, classe);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> List<T> obterLista(String json, Class<T> tClass) {
        CollectionType lista = mapper.getTypeFactory()
                .constructCollectionType(List.class, tClass);

        try {
            return mapper.readValue(json, lista);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
