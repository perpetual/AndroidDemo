package com.example.androiddemo;
import com.example.androiddemo.model.Person;

interface IRemoteService2 {

	String getQuote(in String ticker, in Person requester);
}