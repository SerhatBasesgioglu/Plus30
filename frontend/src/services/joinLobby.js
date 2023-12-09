import axios from "axios";

const joinLobby = async (lobbyId) => {
  try {
    const response = await axios.post("http://localhost:8080/lobby/join", {
      lobbyId: lobbyId,
    });
    console.log(response.data);
  } catch (error) {
    console.log("Join error: " + error);
  }
};

export default joinLobby;
