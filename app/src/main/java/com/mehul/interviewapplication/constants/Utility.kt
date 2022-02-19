package com.mehul.interviewapplication.constants

fun isEmpty(list: List<Any?>?): Boolean = list == null || list.isEmpty()

fun isEmpty(string: String?): Boolean = string == null || string.isEmpty()

fun isNotEmpty(string: String?): Boolean = !isEmpty(string)

fun isNotEmpty(list: List<Any?>?): Boolean = !isEmpty(list)