
export interface EntityExtraction{
  spot: string,
  image?: Image;
  abstract?: string;
  categories?: string[];
}

export interface Image{
  full: string;
  thumbnail: string;
}

export interface LanguageDetection{
  lang: string;
  confidence: number
}

export interface TextSimilarity{
  similarity: number
}

export interface SentimentAnalysis{
  score: number;
  type: string;
}
