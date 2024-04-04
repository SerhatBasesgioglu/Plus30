/* eslint-disable react/prop-types */
import LobbyForm from "./LobbyForm";
import LobbyList from "./LobbyList";
import LobbySettings from "./LobbySettings";
import Miscellaneous from "./Miscellaneous";

import { useState } from "react";
import Button from "components/Button";
const style = "bg-zinc-600  min-h-screen";

const Home = () => {
  const [activeTab, setActiveTab] = useState(1);

  const handleTabClick = (tabNum) => {
    setActiveTab(tabNum);
  };
  return (
    <div className={style}>
      <div className="flex justify-center">
        <Button text="Create Lobby" onClick={() => handleTabClick(1)} />
        <Button text="Lobby List" onClick={() => handleTabClick(2)} />
        <Button text="Lobby Settings" onClick={() => handleTabClick(3)} />
        <Button text="Misc" onClick={() => handleTabClick(4)} />
      </div>
      {activeTab === 1 && <LobbyForm />}
      {activeTab === 2 && <LobbyList />}
      {activeTab === 3 && <LobbySettings />}
      {activeTab === 4 && <Miscellaneous />}
    </div>
  );
};

export default Home;
