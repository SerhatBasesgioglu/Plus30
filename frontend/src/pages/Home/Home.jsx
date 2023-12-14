import Header from "../../components/Header/Header";
import LobbyForm from "../../components/LobbyForm/LobbyForm";
import LobbyList from "../../components/LobbyList/LobbyList";

const Home = () => {
  return (
    <div className="App container bg-success bg-opacity-75">
      <Header />
      <div className="row">
        <LobbyForm className="col-4" />
        <div className="col-2" />
        <LobbyList className="col-6" />
      </div>
    </div>
  );
};

export default Home;
