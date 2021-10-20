package com.example.coronatracker.funtions

import com.example.coronatracker.dataClasses.Root
import com.example.coronatracker.dataClasses.indiaModel.Regional

object ImportantFun {

    /**
     * @return index on which
     * @param state is present in
     * @param list
     */
    fun getStateIndex(list: List<Regional>,state:String) : Int{
        var i=0
        while (i<list.size){
            if (list[i].loc==state)
                return i
            i++
        }
        return -1
    }

    /**
     * @return index on which
     * @param state is present in
     * @param list
     */
    fun getStateContactIndex(list: List<com.example.coronatracker.dataClasses.indiaContactModel.Regional>,
                              state:String) : Int{
        var i=0
        while (i<list.size){
            if (list[i].loc==state)
                return i
            i++
        }
        return -1
    }

    /**
     * @return index on which
     * @param country is present in
     * @param list
     */
    fun getCountryIndex(list: List<Root>,country : String) : Int{
        var i=0
        while (i<list.size){
            if (list[i].country==country)
                return i
            i++
        }
        return -1
    }
}