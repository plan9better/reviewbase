import { StarIcon } from '@heroicons/react/20/solid'
import { useState } from 'react'

// Define types for reviews and the ReviewList component props
type Review = {
  id: number
  game: string
  content: string
  rating: number
  constructive: boolean
}

type ReviewListProps = {
  reviews: Review[]
  user: { id: number } | null // Assuming user has an `id` field or null if not logged in
}

export default function ReviewList({ reviews, user }: ReviewListProps) {
  const [loading, setLoading] = useState(false)

  // Function to handle report
  const handleReport = async (reviewId: number) => {
    if (!user) {
      alert('You must be logged in to report a review.')
      return
    }

    setLoading(true)
    try {
      const response = await fetch(`http://localhost:8080/api/v1/reviews/${reviewId}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ userId: user.id }),
      })

      if (response.ok) {
        alert('Review reported successfully.')
      } else {
        alert('Failed to report the review.')
      }
    } catch (error) {
      console.error('Error reporting review:', error)
      alert('An error occurred. Please try again.')
    } finally {
      setLoading(false)
    }
  }

  return (
      <div className="space-y-4">
        {reviews.map((review) => (
            <div
                key={review.id}
                className={`bg-gray-800 p-4 rounded-lg shadow ${
                    review.constructive ? 'border-l-4 border-green-500' : ''
                }`}
            >
              <h3 className="text-xl font-semibold mb-2">{review.game}</h3>
              <p className="text-gray-300 mb-2">{review.content}</p>
              <div className="flex items-center mb-2">
                <div className="flex items-center mr-2">
                  {[...Array(5)].map((_, i) => (
                      <StarIcon
                          key={i}
                          className={`h-5 w-5 ${i < review.rating ? 'text-yellow-500' : 'text-gray-500'}`}
                      />
                  ))}
                </div>
            {/*    <span className="text-sm text-gray-400">*/}
            {/*  {review.constructive ? 'Constructive' : 'Not Constructive'}*/}
            {/*</span>*/}
              </div>
              {user && (
                  <button
                      onClick={() => handleReport(review.id)}
                      className="text-red-500 hover:underline"
                      disabled={loading}
                  >
                    {loading ? 'Reporting...' : 'Report'}
                  </button>
              )}
            </div>
        ))}
      </div>
  )
}
