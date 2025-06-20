package br.com.alura.literalura.service;

interface IConverteDados {
  <T> T convert(String json, Class<T> classe);
}
