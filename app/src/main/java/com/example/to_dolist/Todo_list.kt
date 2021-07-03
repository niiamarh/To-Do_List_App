package com.example.to_dolist

class Todo_list {

    var Todo_listID:Int?=null
    var Todo_listFName:String?=null
    var Todo_listLName:String?=null
    var Todo_listNumber:String?=null

    constructor(Todo_listID:Int, Todo_listFName:String, Todo_listLName:String, Todo_listNumber:String){
        this.Todo_listID=Todo_listID
        this.Todo_listFName=Todo_listFName
        this.Todo_listLName=Todo_listLName
        this.Todo_listNumber=Todo_listNumber
    }

}