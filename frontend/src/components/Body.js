import handleClick from "../services/createLobby";

const LobbyInfo = () => {
  return (
    <form>
      <input name="query"></input>
      <button type="submit">Search</button>
    </form>
  );
};

const Body = () => {
  return (
    <div>
      <LobbyInfo />
      <button onClick={handleClick}>Create Lobby!</button>
    </div>
  );
};

export default Body;
