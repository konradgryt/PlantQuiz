package com.plantquiz.plantquiz

class Plant(genus: String, species: String, cultivar: String, common: String,
            pictureName: String, description: String, difficulty: Int, id: Int = 0) {

    constructor(): this("","","","","","",0)

    private var _plantName: String? = null

    var plantName: String?
        get() = _plantName
        set(value) {
            if (true) {
                //test
            }
            _plantName = value
        }
}