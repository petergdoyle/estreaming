
import groovy.json.*

def jsonBuilder = new groovy.json.JsonBuilder()
def vals = payload.split(',')
try {
  jsonBuilder.flight{
      airlineCd(vals[0])
      airlineNm(vals[1])
      airlineCntry(vals[2])
      airportCdDpt(vals[3])
      airportNmDpt(vals[4])
      airportCityDpt(vals[5])
      airportCntryDpt(vals[6])
      airportCdArr(vals[7])
      airportNmArr(vals[8])
      airportCityArr(vals[9])
      airportCntryArr(vals[10])
      departTime(vals[11])
      arrTime(vals[12])
      price(Float.parseFloat(vals[13]))
      currency(vals[14])
      fltType(vals[15])
  }
  jsonBuilder.toPrettyString()
} catch (all) {
  println(all)
  println("problem parsing "+payload)
}
