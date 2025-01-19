import Image from 'next/image'

type Game = {
  id: number
  title: string
  imageUrl: string
  rating: number
}

type GameRecommendationsProps = {
  games: Game[]
}

export default function GameRecommendations({ games }: GameRecommendationsProps) {
  if (games.length === 0) {
    return (
      <div className="text-center py-8">
        <p className="text-xl text-gray-400">No games found. Try a different search or request a new game.</p>
      </div>
    )
  }

  return (
    <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6">
      {games.map((game) => (
        <div key={game.id} className="bg-gray-800 rounded-lg shadow overflow-hidden">
          <Image src={game.imageUrl || "/placeholder.svg"} alt={game.title} width={300} height={150} className="w-full h-40 object-cover" />
          <div className="p-4">
            <h3 className="text-lg font-semibold mb-2">{game.title}</h3>
            <div className="flex items-center">
              {/*<span className="text-yellow-500 mr-1">{game.rating.toFixed(1)}</span>*/}
              <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5 text-yellow-500" viewBox="0 0 20 20" fill="currentColor">
                <path d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z" />
              </svg>
            </div>
          </div>
        </div>
      ))}
    </div>
  )
}

