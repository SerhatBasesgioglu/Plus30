import { useState } from "react";
import Button from "components/Button";
import { get, post, remove } from "services/api";
import Table from "components/Table";
const KickList = () => {
  const [summonerName, setSummonerName] = useState("");
  const [summonerList, setSummonerList] = useState([]);
  const [kickBlackList, setKickBlackList] = useState([]);
  const columns = [{ header: "Name", accessor: (row) => `${row.gameName}#${row.tagLine}` }];

  const handleSubmit = async (e) => {
    e.preventDefault();
    const hashPosition = summonerName.indexOf("#");
    let gameName = summonerName.slice(0, hashPosition);
    let tagLine;
    if (hashPosition > -1) tagLine = summonerName.slice(hashPosition + 1);
    let uri = "/summoner/" + gameName + "/" + tagLine;
    await post(uri);
    fetchSummonerList();
  };

  const fetchSummonerList = async () => {
    const summonerListDb = await get("summoner");
    console.log(summonerListDb);
    setSummonerList(summonerListDb);
    console.log(summonerList);
  };

  const deleteSummoner = async (summoner) => {
    try {
      let url = "summoner/" + summoner.gameName + "/" + summoner.tagLine;
      await remove(url);
      fetchSummonerList();
    } catch (error) {
      console.log("hey");
    }
  };

  const handleChange = (e) => {
    setSummonerName(e.target.value);
  };

  return (
    <div>
      <form method="post" onSubmit={handleSubmit}>
        <input name="summonerName" value={summonerName} onChange={handleChange}></input>
        <Button type="submit" text="Add" />
      </form>
      <Button text="Refresh" onClick={fetchSummonerList} />
      <Table columns={columns} data={summonerList} handleRowDoubleClick={(row) => deleteSummoner(row)} />
    </div>
  );
};

export default KickList;
