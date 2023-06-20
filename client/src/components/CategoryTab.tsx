export default function CategoryTab() {
  const categories = ["커피", "라떼", "티", "쥬스", "디카페인"];

  return (
    <nav>
      <ul>
        {categories.map((category) => (
          <TabItem key={category} name={category} />
        ))}
      </ul>
    </nav>
  );
}

interface TabItemProps {
  name: string;
}

function TabItem({ name }: TabItemProps) {
  return <li>{name}</li>;
}
