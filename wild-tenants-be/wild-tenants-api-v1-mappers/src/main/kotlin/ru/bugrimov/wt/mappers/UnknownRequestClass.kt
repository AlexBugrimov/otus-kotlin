package ru.bugrimov.wt.mappers

class UnknownRequestClass(clazz: Class<*>) : RuntimeException("Class $clazz cannot be mapped to WtContext")
