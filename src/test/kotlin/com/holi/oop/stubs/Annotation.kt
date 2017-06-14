package com.holi.oop.stubs

import kotlin.annotation.AnnotationTarget.*

@Target(FILE, PROPERTY, FIELD, PROPERTY_GETTER, PROPERTY_SETTER
        , VALUE_PARAMETER, FUNCTION, TYPE_PARAMETER, CONSTRUCTOR, TYPE, EXPRESSION)
annotation class Annotation;