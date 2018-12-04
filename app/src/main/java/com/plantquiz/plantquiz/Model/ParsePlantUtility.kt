package com.plantquiz.plantquiz.Model

import org.json.JSONArray
import org.json.JSONObject

class ParsePlantUtility {

    fun parsePlantObjectsFromJSONData(): List<Plant>? {

        var allPlantObjects: ArrayList<Plant> = ArrayList()
        var downloadingObject: DownloadingObject = DownloadingObject()
        var topLevelPlantJSONData: String =
            downloadingObject.downloadJSONDataFromLink("http://plantplaces.com/perl/mobile/flashcard.pl")
        var topLevelJSONObject: JSONObject = JSONObject(topLevelPlantJSONData)
        var plantObjectsArray: JSONArray = topLevelJSONObject.getJSONArray("values")

        var index: Int = 0

        while (index < plantObjectsArray.length()) {
            var plantObject: Plant = Plant()
            var currentPlant = plantObjectsArray.getJSONObject(index)

            /*
            plantObject.genus = currentPlant.getString("genus")
            plantObject.species = currentPlant.getString("species")
            plantObject.cultivar = currentPlant.getString("cultivar")
            plantObject.common = currentPlant.getString("common")
            plantObject.pictureName = currentPlant.getString("pictureName")
            plantObject.description = currentPlant.getString("description")
            plantObject.difficulty = currentPlant.getInt("difficulty")
            plantObject.id = currentPlant.getInt("id")
            */

            //Using with to refractor code above
            with(currentPlant) {
                plantObject.genus = getString("genus")
                plantObject.species = getString("species")
                plantObject.cultivar = getString("cultivar")
                plantObject.common = getString("common")
                plantObject.pictureName = getString("pictureName")
                plantObject.description = getString("description")
                plantObject.difficulty = getInt("difficulty")
                plantObject.id = getInt("id")
            }

            allPlantObjects.add(plantObject)

            index++
        }
        return allPlantObjects
    }
}