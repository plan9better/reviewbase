# Reviewbase is a website that filters steams reviews showing you only the constructive ones saving you time and frustration.
## How does it work?
By default the database has only steams short app data (just name and appid)
When user clicks a game they are interested in the app then checks whether its reviews
are already in the DB, if so it serves them. Otherwise it fetches the reviews from steam API,
passes them through the model and serves the constructive ones. This all happens
in about 3 seconds with room for improvement.
