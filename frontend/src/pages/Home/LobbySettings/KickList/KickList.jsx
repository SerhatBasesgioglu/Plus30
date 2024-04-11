import { useState } from "react";
import Button from "components/Button";
import { post } from "services/api";
const KickList = () => {
  const [summoner, setSummoner] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();
    let data = { puuid: summoner };
    post("/summoner/blacklist", data);
  };

  const handleChange = (e) => {
    setSummoner(e.target.value);
  };

  return (
    <div>
      <form method="post" onSubmit={handleSubmit}>
        <input name="summonerName" value={summoner} onChange={handleChange}></input>
        <Button type="submit" text="Add" />
      </form>
    </div>
  );
};

export default KickList;
