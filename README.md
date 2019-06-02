# IDS Project 2018
## Team
- Mattia Righetti
- Manuel Trivilino
- Luca Terracciano

<p align="center">
  <a href="http://floodgategames.com/Sagrada/">
    <img src="https://cf.geekdo-images.com/medium/img/frguV5YOfP1hsAmZpKnYxAmIpYA=/fit-in/500x500/filters:no_upscale()/pic3525224.jpg" alt="" width=381 height=500>
  </a>
  
  <h3 align="center">Sagrada</h3>

  <p align="center">
    Java implementation of the Sagrada Board Game.
    <br>
    <br>
    <a href="https://github.com/MattRighetti/ing-sw-2018-righetti-trivilino-terracciano">Project</a>
    .
    <a href="https://www.youtube.com/watch?v=0JLpaGHL8MQ">How to play</a>
  </p>
</p>

<br>
  
  
## Total coverage
![screenshot sonar](https://user-images.githubusercontent.com/16304728/42348414-a5466a2c-80a9-11e8-8afa-f6deb660af94.png)

## Model Coverage
<img width="1029" alt="screen shot 2018-07-05 at 23 31 56" src="https://user-images.githubusercontent.com/16304728/42349027-a1d95384-80ab-11e8-8620-f7df1ec45716.png">

## Implemented Functions
- Persistence
- Multi match

## Difficulties
RMI disconnection by unplugging cable isn't discovedered

## Documentation

Important: Some SagradaGame.class tests only run if the RMIRegistry is up and running, so before compiling the application with tests
be sure to run the launch_registry.sh file

# How to run JARs

1. Launch the serverStart.sh script
2. Run the Server JAR with the following command

  `java -jar -Djava.rmi.server.hostname=<ServerIP> SERVER.jar <ServerIP>`

3. Run the Client JAR with the following command (same goes for the CLI.jar)

  `java -jar -Djava.rmi.server.hostname=<ServerIP> GUI.jar <ServerIP>`
