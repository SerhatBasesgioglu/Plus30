import { render, screen } from "@testing-library/react";
import Table from ".";

describe("Table component", () => {
  const columns = [
    { header: "Name", accessor: "name" },
    { header: "age", accessor: "age" },
  ];

  const data = [
    { name: "Serhat", age: "24" },
    { name: "Serdar", age: "28" },
  ];

  test("renders with data", () => {
    render(<Table columns={columns} data={data}></Table>);
  });

  test("renders with null data", () => {
    render(<Table columns={columns}></Table>);
  });
});
