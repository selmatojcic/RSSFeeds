package com.example.rssfeedapp.model

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "item", strict = false)
data class Item @JvmOverloads constructor(
    @field:Element(name = "title", required = false)
    @param:Element(name = "title", required = false) var title: String = "",

    @field:Element(name = "description", required = false)
    @param:Element(name = "description", required = false) var description: String = "",

    @field:Element(name = "link", required = false)
    @param:Element(name = "link", required = false) var link: String = "",

    @field:Element(name = "image", required = false)
    @param:Element(name = "image", required = false) var image: Image = Image(),
)