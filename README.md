# Plus30

Currently on development!

Official Website: https://plus30lol.com (empty yet)

Plus30 aims to automate some tasks for the custom game lobbies (Centered around Howling Abyss).

# Current Features

- Blacklisting blocked players
- Creating lobby
- Filtering/entering lobbies
- Lobby Presets

# Planned Features

- Auto Invite
- Extensive Whitelist/Blacklist implementation
- Custom Stats
- Discord Integration (?)
- Custom Ban/Pick System For Champion Selection (?)

# To Do

Backend

- Change connector from sync to async
- Implement websocket client (Currently some functions are called using timers (auto-kick system), websocket will call them when an event happens)
- Implement websocket server
- Implement DTO
- Add a connection to an online db (plus30lol.com will contain an endpoint for fetching global kick list)

Frontend

- Add current lobby data
- Import frontend into an electron instance
- Implement websocket client
- Add pop ups for better user experience (Lobby created, lobby is full, etc.)
- Implement auto-kick toggle, add options (only blocked players, import from local or online list, etc.)

General

- Implement proper error handling
- Write unit test
- Implement security
- Implement automation for releases

# Setup

Release is not available, backend and frontend servers can be run on IntelliJ and VS Code respectively.

An executable can be created using the following steps:

Backend->Jar (.\mvnw clean package)

Frontend->Build (npm run build)

Merge these in an Electron application, create the executable using electron builder. Jar should be called by the frontend app and it should be closed by it as well. Otherwise backend instance will continue to run on background. If that is the case it can be closed from task manager.

# Issues / Contributions

You can reach me with following channels, I appreciate the use of Issues page though (https://github.com/SerhatBasesgioglu/Plus30/issues)

- Riot Id: AyDaKaR#Raven

- Discord: aydakar

# Disclaimer

App is approved by Rioters in developer portal, using it with the current functionalities (27.12.2023) should not cause any problem (slim to none ban risk).

I am prioritizing the technologies that I am learning (Spring Boot, Electron, ReactJs) instead of focusing on performance, RAM usage and storage of the project will be relatively high.

Plus30 isn't endorsed by Riot Games and doesn't reflect the views or opinions of Riot Games or anyone officially involved in producing or managing Riot Games properties. Riot Games, and all associated properties are trademarks or registered trademarks of Riot Games, Inc.
