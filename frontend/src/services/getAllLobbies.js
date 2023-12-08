import axios from "axios";

const getAllLobbies = async () => {
  const response = await axios.get("http://localhost:8080/lobby/all-lobbies");
  return response.data;
};

export default getAllLobbies;
