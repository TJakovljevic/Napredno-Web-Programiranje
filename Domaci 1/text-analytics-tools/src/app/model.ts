export interface Image{
  full: string;
  thumbnail: string;
}

export interface EntityExtraction{
  spot: string,
  image?: Image;
  abstract?: string;
  categories?: string[];
}

export interface Language{
  lang: string;
  confidence: string;
}
