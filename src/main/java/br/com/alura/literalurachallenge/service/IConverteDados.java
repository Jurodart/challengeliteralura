package br.com.alura.literalurachallenge.service;

//5) Convertendo os dados
public interface IConverteDados {
    //Utiliza o Generics, onde T serve como placeholder
    //de alguma variavel ou dados que vai ser colocado depois.
    //Permite ajudar no ConverteDados com o método genérico
    <T> T obterDados(String json, Class <T> classe);
}
