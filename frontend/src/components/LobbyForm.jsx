import { useState } from "react";
import createLobby from "../services/createLobby";

const LobbyForm = () => {
  const [inputs, setInputs] = useState({});

  const handleSubmit = (event) => {
    event.preventDefault();
    createLobby(inputs);
  };

  const handleChange = (event) => {
    const name = event.target.name;
    const value = event.target.value;
    setInputs((prevState) => ({ ...prevState, [name]: value }));
  };

  return (
    <form onSubmit={handleSubmit}>
      <label>
        Lobby Name
        <input
          type="text"
          name="lobbyname"
          value={inputs.lobbyName}
          onChange={handleChange}
        />
      </label>
      <label>
        Password
        <input
          type="text"
          name="lobbypassword"
          value={inputs.lobbyPassword}
          onChange={handleChange}
        />
      </label>
      <label>
        Game Type
        <input
          type="text"
          name="gametype"
          value={inputs.gameType}
          onChange={handleChange}
        />
      </label>
      <label>
        Team Size
        <input
          type="text"
          name="teamsize"
          value={inputs.teamSize}
          onChange={handleChange}
        />
      </label>
      <input type="submit" />
    </form>
  );
};

export default LobbyForm;
