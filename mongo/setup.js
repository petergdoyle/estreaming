use splash
db.createCollection("klrs", { capped : true, size : 5242880, max : 5000 } )
use airshop
db.createCollection("results", { capped : true, size : 5242880, max : 5000 } )
