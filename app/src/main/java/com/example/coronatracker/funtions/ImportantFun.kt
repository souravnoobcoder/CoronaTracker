package com.example.coronatracker.funtions

import com.example.coronatracker.dataClasses.Root
import com.example.coronatracker.dataClasses.indiaModel.Regional
import java.util.*

object ImportantFun {
    fun getStateIndex(list: List<Regional>,state:String) : Int{
        var i=0
        while (i<list.size){
            if (list[i].loc==state)
                return i
            i++
        }
        return -1
    }
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