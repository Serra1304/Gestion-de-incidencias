/* "use client";

import { useState } from "react";
import Container from "../ui/Container";

type ListContainerProps = {
  title?: string;
  items: string[];
  onSelect?: (item: string) => void;
};

export default function ListContainer({ title, items, onSelect }: ListContainerProps) {
  const [selected, setSelected] = useState<string | null>(null);

  const handleClick = (item: string) => {
    setSelected(item);
    onSelect?.(item);
  };

  return (
    <Container title={title}>
      <ul className="space-y-2">
        {items.map((item) => (
          <li
            key={item}
            onClick={() => handleClick(item)}
            className={`cursor-pointer p-1 transition-colors
              ${selected === item 
                ? "item-select" 
                : "item"}
            `}
          >
            {item}
          </li>
        ))}
      </ul>
    </Container>
  );
} */