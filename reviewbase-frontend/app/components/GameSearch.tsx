'use client';

import { useState } from 'react';
import { useRouter } from 'next/navigation';

interface Game {
    id: string;
    title: string;
}

export default function GameSearch() {
    const [searchTerm, setSearchTerm] = useState('');
    const [searchResults, setSearchResults] = useState<Game[]>([]);
    const [isLoading, setIsLoading] = useState(false);
    const [showPopup, setShowPopup] = useState(false);
    const router = useRouter();

    const handleSearch = async (e: React.ChangeEvent<HTMLInputElement>) => {
        const term = e.target.value;
        setSearchTerm(term);

        if (term.trim() !== '') {
            try {
                const response = await fetch(`http://localhost:8080/api/v1/game/search?query=${term}`);
                if (response.ok) {
                    const results: Game[] = await response.json();
                    setSearchResults(results);
                } else {
                    console.error('Error fetching search results:', response.statusText);
                }
            } catch (error) {
                console.error('Error fetching search results:', error);
            }
        } else {
            setSearchResults([]);
        }
    };

    const handleResultClick = (gameId: string) => {
        setShowPopup(true);
        setIsLoading(true);

        setTimeout(() => {
            setShowPopup(false); // Hide the pop-up after a delay
            router.push(`/game/${gameId}`);
        }, 2000); // Simulates a slight delay before navigation
    };

    return (
        <div>
            <input
                type="text"
                value={searchTerm}
                onChange={handleSearch}
                placeholder="Search games..."
                className="w-full p-2 border rounded mb-4"
            />
            <ul>
                {searchResults.map((game) => (
                    <li
                        key={game.id}
                        onClick={() => handleResultClick(game.id)}
                        className="cursor-pointer p-2 border-b hover:bg-gray-100"
                    >
                        {game.title}
                    </li>
                ))}
            </ul>

            {isLoading && (
                <div className="fixed inset-0 bg-gray-800 bg-opacity-50 flex items-center justify-center">
                    <div className="bg-white p-6 rounded shadow-md text-center">
                        <div className="loader mb-4"></div>
                        <p>Loading... Please wait.</p>
                    </div>
                </div>
            )}

            {showPopup && (
                <div className="fixed inset-0 bg-gray-800 bg-opacity-50 flex items-center justify-center">
                    <div className="bg-white p-6 rounded shadow-md text-center">
                        <p>This game might take a few seconds to load if it’s not in the database yet.</p>
                    </div>
                </div>
            )}

            <style jsx>{`
        .loader {
          border: 4px solid #f3f3f3;
          border-top: 4px solid #3498db;
          border-radius: 50%;
          width: 40px;
          height: 40px;
          animation: spin 1s linear infinite;
        }
        @keyframes spin {
          0% {
            transform: rotate(0deg);
          }
          100% {
            transform: rotate(360deg);
          }
        }
      `}</style>
        </div>
    );
}
