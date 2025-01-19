'use client'

import { useState } from 'react'
import Header from './components/Header'
import ReviewList from './components/ReviewList'
import GameRecommendations from './components/GameRecommendations'
import GameSearch from './components/GameSearch'

// Mock data for reviews and game recommendations
const mockReviews = [
  { id: 1, game: "Elden Ring", content: "An incredible open-world experience with challenging combat.", rating: 5, constructive: true },
  { id: 2, game: "Cyberpunk 2077", content: "Impressive visuals, but still has some bugs.", rating: 3, constructive: true },
  { id: 3, game: "Valheim", content: "Addictive survival game with friends.", rating: 4, constructive: false },
]

const mockRecommendations = [
  { id: 1, title: "The Witcher 3", imageUrl: "/placeholder.svg?height=150&width=300", rating: 4.8 },
  { id: 2, title: "Red Dead Redemption 2", imageUrl: "/placeholder.svg?height=150&width=300", rating: 4.7 },
  { id: 3, title: "Hades", imageUrl: "/placeholder.svg?height=150&width=300", rating: 4.9 },
  { id: 4, title: "Stardew Valley", imageUrl: "/placeholder.svg?height=150&width=300", rating: 4.6 },
]

export default function Home() {
  const [filteredGames, setFilteredGames] = useState(mockRecommendations)

  const handleSearch = (results: typeof mockRecommendations) => {
    setFilteredGames(results)
  }

  return (
    <div className="min-h-screen flex flex-col">
      <Header />
      <main className="flex-grow container mx-auto px-6 py-8">
        <h1 className="text-4xl font-bold mb-8">Welcome to ReviewBase</h1>
        
        <GameSearch games={mockRecommendations} onSearch={handleSearch} />
        
        <section className="mb-12">
          <h2 className="text-2xl font-semibold mb-4">Constructive Steam Reviews</h2>
          <ReviewList reviews={mockReviews} />
        </section>
        
        <section>
          <h2 className="text-2xl font-semibold mb-4">Recommended Games</h2>
          <GameRecommendations games={filteredGames} />
        </section>
      </main>
      <footer className="bg-gray-800 text-center py-4">
        <p>&copy; 2023 ReviewBase. All rights reserved.</p>
      </footer>
    </div>
  )
}

