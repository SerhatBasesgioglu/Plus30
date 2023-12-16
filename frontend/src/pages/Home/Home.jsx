import Header from "./Header";
import LobbyForm from "./LobbyForm";
import LobbyList from "./LobbyList";
import Lobby from "./Lobby";

const Home = () => {
  return (
    <div className="App container bg-success bg-opacity-75">
      <Header />
      <div className="row">
        <LobbyForm className="col-3" />
        <div className="col-3" />
        <LobbyList className="col-6" />
      </div>
      <div className="row">
        <Lobby className="col-6" />
      </div>
    </div>
  );
};

export default Home;
