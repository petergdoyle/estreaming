use airshop
db.results.find({},{_id:1}).skip(100).limit(1).pretty()
