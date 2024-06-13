import { useState } from "react";
import Button from "components/Button";
import { get } from "services/api";
import KickList from "./KickList";

const LobbySettings = () => {
  const [kickBlocked, setKickBlocked] = useState(true);

  const handleBlockedToggle = async () => {
    setKickBlocked(!kickBlocked);
    if (kickBlocked) {
      await get("/lobby/start-kicker");
    } else {
      await get("/lobby/stop-kicker");
    }
    console.log(kickBlocked);
  };

  return (
    <div className="mt-4">
      <Button
        className={!kickBlocked ? "bg-green-400 hover:bg-green-500" : "bg-red-400 hover:bg-red-500"}
        text="Kick Blocked"
        onClick={handleBlockedToggle}
      />

      <KickList />
    </div>
  );
};

export default LobbySettings;
